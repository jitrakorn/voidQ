import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { NgForm } from '@angular/forms';
import { Booking } from '../booking';
import { BookingService } from '../booking.service';
import { NavController } from '@ionic/angular';
import { Location } from '@angular/common';
import {ToastController} from '@ionic/angular';
import { ConstantPool } from '@angular/compiler';
@Component({
	selector: 'app-clinic-details',
	templateUrl: './clinic-details.page.html',
	styleUrls: ['./clinic-details.page.scss'],
})
export class ClinicDetailsPage implements OnInit {
	clinic: Object;
	submitted: boolean;
	visitreason: String;
	toast:any;  message: string;

	constructor(public sessionService: SessionService, private bookingService: BookingService, private navigationCtrl: NavController,private location: Location,private toastController: ToastController) {
		this.submitted = false;
	}

	ngOnInit() {
		var clinicVals = Object.values(this.sessionService.getClinicObj());
		this.clinic = {
			clinicId: clinicVals[0],
			address: clinicVals[1],
			clinicName: clinicVals[2],
			applicationStatus: clinicVals[3],
			description: clinicVals[4],
			lat: clinicVals[5],
			lng: clinicVals[6],
			phoneNum: clinicVals[7],
			unitPrice: clinicVals[8],
			queueNum: clinicVals[9]
		}
	}

	ionViewWillEnter() {
		
	}
	
	back()
	{
		this.navigationCtrl.navigateBack("/tabs");
	}
	

	async makeBooking(makeBookingForm: NgForm) {
		this.submitted = true;

		console.log(Object.values(this.sessionService.getClinicObj())[0].toString());
		console.log(this.visitreason);
		console.log(Object.values(this.sessionService.getCurrentPatient())[0].toString());

		if (makeBookingForm.valid) {
			await this.bookingService.createBooking(Object.values(this.sessionService.getCurrentPatient())[0].toString(), this.visitreason.toString(), Object.values(this.sessionService.getClinicObj())[0].toString()).subscribe(
				response => {
					
					
					let newBookingId: number = response.bookingId;
					this.sessionService.setBookingId(newBookingId);
					this.message = "booked ! ";
					this.presentToast();
					this.navigationCtrl.navigateRoot("/tabs");
					alert("Booking Successful!");
				},
				error => {
					this.message = "An error has occurred while booking: " + error;
					this.presentToast();
				}
			)
		}
	}


	async presentToast() {
		this.toast = await this.toastController.create({
		  message: this.message,
		  duration: 2000,
		  position: 'bottom'
		});
		await this.toast.present();
		console.log("toast");
	  }
}
