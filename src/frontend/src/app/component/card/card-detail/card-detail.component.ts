import {ChangeDetectionStrategy, Component, OnInit} from '@angular/core';
import {BehaviorSubject, Observable, catchError, map, of, startWith, switchMap } from 'rxjs';
import { CustomHttpResponse } from 'src/app/interface/appStates';
import { State } from 'src/app/interface/state';
import {Biodata} from "../../../interface/biodata";
import {Card} from "../../../interface/card";
import { User } from 'src/app/interface/user';
import { DataState } from 'src/app/enum/dataStates.enum';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { BiodataService } from 'src/app/service/biodata.service';
import {jsPDF as pdf} from 'jspdf';
const CARD_ID = 'id';

@Component({
  selector: 'app-card',
  templateUrl: './card-detail.component.html',
  styleUrls: ['./card-detail.component.css'],
    changeDetection: ChangeDetectionStrategy.OnPush

})
export class CardDetailComponent implements OnInit{
  invoiceState$: Observable<State<CustomHttpResponse<Biodata & Card & User>>>;
  private dataSubject = new BehaviorSubject<CustomHttpResponse<Biodata & Card & User>>(null);
  private isLoadingSubject = new BehaviorSubject<boolean>(false);
  isLoading$ = this.isLoadingSubject.asObservable();
  readonly DataState = DataState;

  constructor(private activatedRoute: ActivatedRoute, private customerService: BiodataService) {
  }
  ngOnInit(): void {
    this.invoiceState$ = this.activatedRoute.paramMap.pipe(
        switchMap((params: ParamMap) => {
          return this.customerService.card$(+params.get(CARD_ID))
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

  exportAsPDF(): void {
    const filename = `invoice-${this.dataSubject.value.data['invoice'].invoiceNumber}.pdf`;
    const doc = new pdf();
    doc.html(document.getElementById('invoice'), { margin: 5, windowWidth: 1000, width: 200,
      callback: (invoice) => invoice.save(filename) });
  }
}

