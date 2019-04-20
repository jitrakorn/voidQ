import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ErrorObservable } from 'rxjs/observable/ErrorObservable';

import { Patient } from './patient';


const httpOptions = {
	
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

const httpOptions2 = {
	headers: new HttpHeaders({ 'Content-Type': 'text/plain' })
};

@Injectable({
	providedIn: 'root'
})
export class PatientService {
	//baseUrl: string = "/api/Patient";
	baseUrl: string = "http://localhost:8080/voidQRWS/Resources/Patient";

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

	resetPassword(username: string): Observable<any> {

		return this.httpClient.get<any>(this.baseUrl + "/resetPassword?username=" + username).pipe
			(
				catchError(this.handleError)
			);
	}



	sendSMS()
	{

		var request = new XMLHttpRequest()

		request.open('POST', 'https://crossorigin.me/https://rest.nexmo.com/sms/json?api_key=7f783f15&api_secret=0140f14a&from=voidQ&to=+6596658673&text=your new password is', true)
		request.onload = function() {
		  // Begin accessing JSON data here
		  var data = JSON.parse(this.response)
		
		  
		}
		
		request.send()

		
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
