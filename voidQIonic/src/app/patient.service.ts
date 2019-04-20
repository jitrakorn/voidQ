import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import { Patient } from './patient';
import { EmailValidator } from '@angular/forms';


const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
	providedIn: 'root'
})
export class PatientService {
	baseUrl: string = "http://localhost:8090/voidQRWS/Resources/Patient";

	constructor(private httpClient: HttpClient) { }


	patientLogin(username: string, password: string): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/patientLogin?username=" + username + "&password=" + password).pipe
			(
				catchError(this.handleError)
			);
	}

	patientRegister(newPatient: Patient): Observable<any> {

		let createPatientReq = {
			"patientEntity": newPatient
		};

		return this.httpClient.put<any>(this.baseUrl + "/createPatient", createPatientReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	updatePatientDetails(email: String, password: String, newPatient: Patient): Observable<any> {
		let updatePatientReq = {
			"email": email,
			"password": password,
			"patientEntity": newPatient
		};
		return this.httpClient.post<any>(this.baseUrl , updatePatientReq, httpOptions).pipe
		(
			catchError(this.handleError)
		);
	}

	updatePatientPassword(oldPassword: String, newPassword: String, newPatient: Patient): Observable<any> {
		let updatePatientPasswordReq = {
			"oldPassword": oldPassword,
			"newPassword": newPassword,
			"patientEntity": newPatient
		};
		return this.httpClient.post<any>(this.baseUrl + "/updatePassword" ,  updatePatientPasswordReq, httpOptions).pipe
		(
			catchError(this.handleError)
		);
	}

	retrieveCurrentBookingQueuePosition(bookingId: string, clinicId: string): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveCurrentBookingQueuePosition?bookingId=" + bookingId + "&clinicId=" + clinicId).pipe
			(
				catchError(this.handleError)
			)
	}

	retrieveCurrentBooking(patientId: string): Observable<any> {
		return this.httpClient.get<any>(this.baseUrl + "/retrieveCurrentBooking/" + patientId).pipe
			(
				catchError(this.handleError)
			)
	}

	private handleError(error: HttpErrorResponse) {
		let errorMessage: string = "";

		if (error.error instanceof ErrorEvent) {
			errorMessage = "An unknown error has occurred: " + error.error.message;
		}
		else {
			errorMessage = "A HTTP error has occurred: " + `HTTP ${error.status}: ${error.error.message}`;
		}

		console.error(errorMessage);

		return throwError(errorMessage);
	}

}
