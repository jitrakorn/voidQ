import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import { Patient } from './patient';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class PatientService {
  baseUrl: string = "http://localhost:8090/voidQRWS/Resources/Patient";

  constructor(private httpClient: HttpClient) { }


  patientLogin(email: string, password: string): Observable<any>
  {
    return this.httpClient.get<any>(this.baseUrl + "/patientLogin?email=" + email + "&password=" + password).pipe
    (
        catchError(this.handleError)
    );
  }

  patientRegister(newPatient: Patient): Observable<any>
  {

    let createPatientReq = {
      "patientEntity": newPatient 
    };

    return this.httpClient.put<any>(this.baseUrl + "/createPatient"  , createPatientReq, httpOptions).pipe
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
