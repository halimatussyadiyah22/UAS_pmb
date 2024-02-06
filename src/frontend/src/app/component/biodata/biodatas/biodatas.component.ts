import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith } from 'rxjs';
import { CustomHttpResponse, Page } from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import {Biodata} from "../../../interface/biodata";
import { User } from 'src/app/interface/user';
import { DataState } from 'src/app/enum/dataStates.enum';
import { Router } from '@angular/router';
import { BiodataService } from 'src/app/service/biodata.service';
import { NgForm } from '@angular/forms';
import { HttpEvent, HttpEventType } from '@angular/common/http';
import { NotificationService } from 'src/app/service/notification.service';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-biodatas',
  templateUrl: './biodatas.component.html',
  styleUrls: ['./biodatas.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class BiodatasComponent implements OnInit{
  customersState$: Observable<State<CustomHttpResponse<Page<Biodata> & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<Biodata> & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  private showLogsSubject = new BehaviorSubject<boolean>(false);
  showLogs$ = this.showLogsSubject.asObservable();
  readonly DataState = DataState;
  private fileStatusSubject = new BehaviorSubject<{ status: string, type: string, percent: number }>(undefined);

  constructor(private router: Router, private biodataService: BiodataService, private notification: NotificationService) { }

  ngOnInit(): void {
    this.customersState$ = this.biodataService.searchBiodatas$()
        .pipe(
            map(response => {
              console.log(response);
              this.dataSubject.next(response);
              return { dataState: DataState.LOADED, appData: response };
            }),
            startWith({ dataState: DataState.LOADING }),
            catchError((error: string) => {
              return of({ dataState: DataState.ERROR, error })
            })
        )
  }

  searchCustomers(searchForm: NgForm): void {
    this.currentPageSubject.next(0);
    this.customersState$ = this.biodataService.searchBiodatas$(searchForm.value.name)
        .pipe(
            map(response => {
              console.log(response);
              this.dataSubject.next(response);
              return { dataState: DataState.LOADED, appData: response };
            }),
            startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
            catchError((error: string) => {
              return of({ dataState: DataState.ERROR, error })
            })
        )
  }

  goToPage(pageNumber?: number, name?: string): void {
    this.customersState$ = this.biodataService.searchBiodatas$(name, pageNumber)
        .pipe(
            map(response => {
              console.log(response);
              this.dataSubject.next(response);
              this.currentPageSubject.next(pageNumber);
              return { dataState: DataState.LOADED, appData: response };
            }),
            startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
            catchError((error: string) => {
              return of({ dataState: DataState.LOADED, error, appData: this.dataSubject.value })
            })
        )
  }

  goToNextOrPreviousPage(direction?: string, name?: string): void {
    this.goToPage(direction === 'forward' ? this.currentPageSubject.value + 1 : this.currentPageSubject.value - 1, name);
  }

  selectBiodata(biodata: Biodata): void {
    this.router.navigate([`/biodatas/${biodata.id}`]);
  }
    report(): void {
        this.customersState$ = this.biodataService.downloadBiodatas$()
            .pipe(
                map(response => {
                    console.log(response);
                    this.reportProgress(response);
                    return {dataState: DataState.LOADED, appData: this.dataSubject.value};
                }),
                startWith({dataState: DataState.LOADED, appData: this.dataSubject.value}),
                catchError((error: string) => {
                    this.notification.onError(error);
                    return of({dataState: DataState.LOADED, error, appData: this.dataSubject.value})
                })
            )
    }
    private reportProgress(httpEvent: HttpEvent<string[] | Blob>): void {
        switch (httpEvent.type) {
            case HttpEventType.DownloadProgress || HttpEventType.UploadProgress:
                this.fileStatusSubject.next({
                    status: 'progress',
                    type: 'Downloading...',
                    percent: Math.round(100 * httpEvent.loaded / httpEvent.total)
                });
                break;
            case HttpEventType.ResponseHeader:
                console.log('Got response Headers', httpEvent);
                break;
            case HttpEventType.Response:
                this.notification.onDefault('Downloading file...');
                saveAs(new File([<Blob>httpEvent.body], httpEvent.headers.get('File-Name'),
                    {type: `${httpEvent.headers.get('Content-Type')};charset-utf-8`}));
                this.fileStatusSubject.next(undefined);
                break;
            default:
                console.log(httpEvent);
                break;
        }
    }

}
