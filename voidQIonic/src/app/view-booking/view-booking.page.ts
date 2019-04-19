import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { NavController } from '@ionic/angular';
import { PatientService } from '../patient.service';
import { BookingService } from '../booking.service';
import { PayPal, PayPalPayment, PayPalConfiguration } from '@ionic-native/paypal/ngx';
import { Location } from '@angular/common';

@Component({
	selector: 'app-view-booking',
	templateUrl: './view-booking.page.html',
	styleUrls: ['./view-booking.page.scss'],
})
export class ViewBookingPage implements OnInit {
	currentUserCurrentQueuePosition: number;
	bookingId: number;
	clinicId: number;
	queueNum: number;
	disabled : any;

	constructor(public sessionService: SessionService, private bookingService: BookingService, private patientService: PatientService, private navigationCtrl: NavController, private payPal: PayPal,private location: Location) { }

	ngOnInit() {
		this.bookingId = this.sessionService.getBookingId();
		this.clinicId = Object.values(this.sessionService.getClinicObj())[0];
	}

	back()
	{
		this.navigationCtrl.navigateRoot('/tabs');
	}
	ionViewWillEnter() {
		this.patientService.retrieveCurrentBookingQueuePosition(String(this.bookingId), String(this.clinicId)).subscribe(
			response => {
				this.queueNum = response.queueNumber
			}
		)
		console.log("BookingId, ClinicId: " + this.bookingId + ", " + this.clinicId);
	}

	async checkin() {
		await this.bookingService.checkin(String(this.bookingId)).subscribe(
			response => {
				console.log(response);
				this.disabled  = true;
								alert("Checked-in!")
			},
			error => {
				console.log(error);
			}
		);
		
		
		
	}


	pay() {

		this.payPal.init({
			PayPalEnvironmentProduction: 'ASbfnf2jyx2vJzLGRfvXDjy9l-DYbFEC-ZoGDSRxC3ay5klM8ieP0cYAUp0_nvurifNktv5A99DBomNK',
			PayPalEnvironmentSandbox: 'ASbfnf2jyx2vJzLGRfvXDjy9l-DYbFEC-ZoGDSRxC3ay5klM8ieP0cYAUp0_nvurifNktv5A99DBomNK'
		}).then(() => {

			// Environments: PayPalEnvironmentNoNetwork, PayPalEnvironmentSandbox, PayPalEnvironmentProduction
			this.payPal.prepareToRender('PayPalEnvironmentSandbox', new PayPalConfiguration({
				// Only needed if you get an "Internal Service Error" after PayPal login!
				//payPalShippingAddressOption: 2 // PayPalShippingAddressOptionPayPal
			})).then(() => {
				let payment = new PayPalPayment('3.33', 'USD', 'Description', 'sale');
				this.payPal.renderSinglePaymentUI(payment).then(() => {
					// Successfully paid

					// Example sandbox response
					//
					// {
					//   "client": {
					//     "environment": "sandbox",
					//     "product_name": "PayPal iOS SDK",
					//     "paypal_sdk_version": "2.16.0",
					//     "platform": "iOS"
					//   },
					//   "response_type": "payment",
					//   "response": {
					//     "id": "PAY-1AB23456CD789012EF34GHIJ",
					//     "state": "approved",
					//     "create_time": "2016-10-03T13:33:33Z",
					//     "intent": "sale"
					//   }
					// }
				}, (error) => {
					console.log(error);
					// Error or render dialog closed without being successful
				});
			}, (error) => {
				console.log(error);
				// Error in configuration
			});
		}, (error) => {
			console.log(error);
			// Error in initialization, maybe PayPal isn't supported or something else
		});
	}
}
