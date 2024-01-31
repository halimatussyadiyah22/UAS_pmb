import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith, switchMap } from 'rxjs';
import {BiodataState, CustomHttpResponse} from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import {BiodataService} from "../../../service/biodata.service";
import { DataState } from 'src/app/enum/dataStates.enum';
import { ActivatedRoute } from '@angular/router';
import { NgForm } from '@angular/forms';
import { ParamMap } from '@angular/router';

@Component({
  selector: 'app-biodata',
  templateUrl: './biodata-detail.component.html',
  styleUrls: ['./biodata-detail.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class BiodataDetailComponent implements OnInit{
  biodataState$: Observable<State<CustomHttpResponse<BiodataState>>>;
  readonly DataState = DataState;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<BiodataState>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  private readonly BIODATA_ID: string = 'id';

  constructor(private activatedRoute: ActivatedRoute, private biodataService: BiodataService) {
  }

  ngOnInit(): void {
    this.biodataState$ = this.activatedRoute.paramMap.pipe(
        switchMap((params: ParamMap) => {
          return this.biodataService.biodata$(+params.get(this.BIODATA_ID))
              .pipe(
                  map(response => {
                    console.log(response);
                    this.dataSubject.next(response);
                    return {dataState: DataState.LOADED, appData: response};
                  }),
                  startWith({dataState: DataState.LOADING}),
                  catchError((error: string) => {
                    return of({dataState: DataState.ERROR, error})
                  })
              )
        })
    );
  }

  updateBiodata(biodataForm: NgForm): void {
    this.isLoadingSubject.next(true);
    this.biodataState$ = this.biodataService.update$(biodataForm.value)
        .pipe(
            map(response => {
              console.log(response);
              this.dataSubject.next({
                ...response,
                data: {
                  ...response.data,
                  biodata: {
                    ...response.data.biodata,
                    cards: this.dataSubject.value.data.biodata.cards
                  }
                }
              });

              this.isLoadingSubject.next(false);
              return {dataState: DataState.LOADED, appData: this.dataSubject.value};
            }),
            startWith({dataState: DataState.LOADED, appData: this.dataSubject.value}),
            catchError((error: string) => {
              this.isLoadingSubject.next(false);
              return of({dataState: DataState.ERROR, error})
            })
        )
  }

}
