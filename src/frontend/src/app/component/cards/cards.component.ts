import {ChangeDetectionStrategy, Component } from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith } from 'rxjs';
import { CustomHttpResponse, Page } from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import {Card} from "../../interface/card";
import { User } from 'src/app/interface/user';
import { DataState } from 'src/app/enum/dataStates.enum';
import { Router } from '@angular/router';
import { BiodataService } from 'src/app/service/biodata.service';

@Component({
  selector: 'app-cards',
  templateUrl: './cards.component.html',
  styleUrls: ['./cards.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class CardsComponent {
  cardState$: Observable<State<CustomHttpResponse<Page<Card> & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Page<Card> & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  private currentPageSubject = new BehaviorSubject<number>(0);
  currentPage$ = this.currentPageSubject.asObservable();
  private showLogsSubject = new BehaviorSubject<boolean>(false);
  showLogs$ = this.showLogsSubject.asObservable();
  readonly DataState = DataState;

  constructor(private router: Router, private biodataService: BiodataService) { }

  ngOnInit(): void {
    this.cardState$ = this.biodataService.cards$()
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

  goToPage(pageNumber?: number): void {
    this.cardState$ = this.biodataService.cards$(pageNumber)
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

  goToNextOrPreviousPage(direction?: string): void {
    this.goToPage(direction === 'forward' ? this.currentPageSubject.value + 1 : this.currentPageSubject.value - 1);
  }

}

