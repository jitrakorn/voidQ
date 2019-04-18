import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ClinicService {
  baseUrl: string = "http://localhost:8080/voidQRWS/Resources/Clinic";
  constructor(private httpClient: HttpClient) { }

  retrieveClinics(): Observable<any>
  {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveAllActivatedClinics").pipe
    (
        catchError(this.handleError)
    );
  }

  retrieveCurrentClinicCurrentDayCurrentQueue(clinicId:string): Observable<any>
  {
    return this.httpClient.get<any>(this.baseUrl + "/retrieveCurrentClinicCurrentDayCurrentQueue?clinicId=" + clinicId).pipe
    (
        catchError(this.handleError)
    );
  }


  private handleError(error: HttpErrorResponse)
  {
    let errorMessage: string = "";

    if (error.error instanceof ErrorEvent)
    {
      errorMessage = "An unknown error has occurred: " + error.error.message;
    }
    else
    {
      errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
    }

    console.error(errorMessage);

    return throwError(errorMessage);
  }
}
