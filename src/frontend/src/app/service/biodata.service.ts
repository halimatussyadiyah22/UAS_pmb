import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import {BiodataState, CustomHttpResponse, Page} from '../interface/appStates';
import { User } from '../interface/user';
import { HttpClient, HttpErrorResponse, HttpEvent } from '@angular/common/http';
import { Biodata } from '../interface/biodata';
import { Stats } from '../interface/stats';
import {Card} from "../interface/card";

@Injectable({
  providedIn: 'root'
})
export class BiodataService {
  private readonly server: string = 'http://localhost:8080';
  constructor(private http: HttpClient) { }
  biodatas$ = (page: number = 0) => <Observable<CustomHttpResponse<Page<Biodata> & User & Stats>>>
      this.http.get<CustomHttpResponse<Page<Biodata> & User & Stats>>
      (`${this.server}/biodata/list?page=${page}`)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );

  biodata$ = (customerId: number) => <Observable<CustomHttpResponse<BiodataState>>>
      this.http.get<CustomHttpResponse<BiodataState>>
      (`${this.server}/biodata/get/${customerId}`)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );
  newBiodata$ = (biodata: Biodata) => <Observable<CustomHttpResponse<Biodata & User>>>
      this.http.post<CustomHttpResponse<Biodata & User>>
      (`${this.server}/biodata/create`, biodata)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );
  update$ = (biodata: Biodata) => <Observable<CustomHttpResponse<BiodataState>>>
      this.http.put<CustomHttpResponse<BiodataState>>
      (`${this.server}/biodata/update`, biodata)
          .pipe(
              tap(console.log),
              catchError(this.handleError)
          );
  searchBiodatas$ = (name: string = '', page: number = 0) => <Observable<CustomHttpResponse<Page<Biodata> & User>>>
        this.http.get<CustomHttpResponse<Page<Biodata> & User>>
        (`${this.server}/biodata/search?name=${name}&page=${page}`)
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );
  private handleError(error: HttpErrorResponse): Observable<never> {
    console.log(error);
    let errorMessage: string;
    if (error.error instanceof ErrorEvent) {
      errorMessage = `A client error occurred - ${error.error.message}`;
    } else {
      if (error.error.reason) {
        errorMessage = error.error.reason;
        console.log(errorMessage);
      } else {
        errorMessage = `An error occurred - Error status ${error.status}`;
      }
    }
    return throwError(() => errorMessage);
  }
    newCard$ = () => <Observable<CustomHttpResponse<Biodata[] & User>>>
        this.http.get<CustomHttpResponse<Biodata[] & User>>
        (`${this.server}/biodata/card/new`)
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );
    createCard$ = (biodataId: number, card: Card) => <Observable<CustomHttpResponse<Biodata[] & User>>>
        this.http.post<CustomHttpResponse<Biodata[] & User>>
        (`${this.server}/biodata/card/addtobiodata/${biodataId}`, card)
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );

    cards$ = (page: number = 0) => <Observable<CustomHttpResponse<Page<Card> & User>>>
        this.http.get<CustomHttpResponse<Page<Card> & User>>
        (`${this.server}/biodata/card/list?page=${page}`)
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );
    card$ = (invoiceId: number) => <Observable<CustomHttpResponse<Biodata & Card & User>>>
        this.http.get<CustomHttpResponse<Biodata & Card & User>>
        (`${this.server}/biodata/card/get/${invoiceId}`)
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );
    downloadBiodatas$ = () => <Observable<HttpEvent<Blob>>>
        this.http.get(`${this.server}/biodata/download/biodatas`,
            { reportProgress: true, observe: 'events', responseType: 'blob' })
            .pipe(
                tap(console.log),
                catchError(this.handleError)
            );

}
