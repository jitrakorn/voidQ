import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

const httpOptions = {
	headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
	providedIn: 'root'
})
export class BookingService {
	baseUrl: string = "http://localhost:8080/voidQRWS/Resources/Booking";

	constructor(private httpClient: HttpClient) { }

	createBooking(email: String, visitReason: String, clinicId: String): Observable<any> {
		let createBookingReq = {
			"email": email,
			"visitReason": visitReason,
			"clinicId": clinicId
		}
		return this.httpClient.post<any>(this.baseUrl + "/createBooking", createBookingReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
	}

	checkin(bookingId: String): Observable<any> {
		let checkInReq = {
			"bookingId": bookingId
		}
		return this.httpClient.put<any>(this.baseUrl + "/checkin", checkInReq, httpOptions).pipe
			(
				catchError(this.handleError)
			);
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
