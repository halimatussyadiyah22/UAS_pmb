import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith } from 'rxjs';
import { CustomHttpResponse } from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import {Biodata} from "../../interface/biodata";
import { User } from 'src/app/interface/user';
import { DataState } from 'src/app/enum/dataStates.enum';
import { BiodataService } from 'src/app/service/biodata.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-new-card',
  templateUrl: './new-card.component.html',
  styleUrls: ['./new-card.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class NewCardComponent implements OnInit{
  newCardState$: Observable<State<CustomHttpResponse<Biodata[] & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Biodata[] & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  readonly DataState = DataState;

  constructor(private biodataService: BiodataService) { }

  ngOnInit(): void {
    this.newCardState$ = this.biodataService.newCard$()
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

  newCard(newCardForm: NgForm): void {
    this.dataSubject.next({ ...this.dataSubject.value, message: null });
    this.isLoadingSubject.next(true);
    this.newCardState$ = this.biodataService.createCard$(newCardForm.value.biodataId, newCardForm.value)
        .pipe(
            map(response => {
              console.log(response);
              newCardForm.reset({ status: 'PENDING' });
              this.isLoadingSubject.next(false);
              this.dataSubject.next(response);
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
