import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith } from 'rxjs';
import { DataState } from 'src/app/enum/dataStates.enum';
import { CustomHttpResponse, Page } from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import { User } from 'src/app/interface/user';
import {BiodataService} from "../../service/biodata.service";
import { NgForm } from '@angular/forms';
import {Biodata} from "../../interface/biodata";
import {Stats} from "../../interface/stats";

@Component({
  selector: 'app-new-biodata',
  templateUrl: './new-biodata.component.html',
  styleUrls: ['./new-biodata.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class NewBiodataComponent implements OnInit{
  newBiodataState$: Observable<State<CustomHttpResponse<Page<Biodata> & User & Stats>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<Biodata> & User & Stats>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  readonly DataState = DataState;

  constructor(private biodataService: BiodataService) { }

  ngOnInit(): void {
    this.newBiodataState$ = this.biodataService.biodatas$()
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

  createBiodata(newBiayaForm: NgForm): void {
    this.isLoadingSubject.next(true);
    this.newBiodataState$ = this.biodataService.newBiodata$(newBiayaForm.value)
        .pipe(
            map(response => {
              console.log(response);
              newBiayaForm.reset({ type: 'INDIVIDUAL', status: 'ACTIVE' });
              this.isLoadingSubject.next(false);
              return { dataState: DataState.LOADED, appData: this.dataSubject.value };
            }),
            startWith({ dataState: DataState.LOADED, appData: this.dataSubject.value }),
            catchError((error: string) => {
              this.isLoadingSubject.next(false);
              return of({ dataState: DataState.LOADED, error })
            })
        )
  }

}
