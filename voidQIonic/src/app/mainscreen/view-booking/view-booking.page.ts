import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/session.service';
import { BookingService } from 'src/app/booking.service';
import { PatientService } from 'src/app/patient.service';
import { PayPal, PayPalPayment, PayPalConfiguration } from '@ionic-native/paypal/ngx';
import { NavController } from '@ionic/angular';
import { Booking } from 'src/app/booking';
import { checkAndUpdateBinding } from '@angular/core/src/view/util';
@Component({
  selector: 'app-view-booking',
  templateUrl: './view-booking.page.html',
  styleUrls: ['./view-booking.page.scss'],
})
export class ViewBookingPage implements OnInit {

  currentUserCurrentQueuePosition: number;
	
	clinicEntity: any;
	queueNum: number;
	booking : any;
	disabled : any;
	paymentDisabled : any;
	constructor(public sessionService: SessionService, private bookingService: BookingService, private patientService: PatientService, private navigationCtrl: NavController, private payPal: PayPal) { }

	async ngOnInit() {
		
	
	}
 async checkPatient()
 {
	await this.patientService.retrieveCurrentBooking(Object.values(this.sessionService.getCurrentPatient())[1]).subscribe(
		response => {
			this.booking = response.bookingEntity;
			console.log(this.booking);
			if(this.booking.status == "BOOKED")
			{
				this.disabled = false;
			}
			else{
				this.disabled  = true;
			}
			if(this.booking.status == "PAID" )
			{
				this.paymentDisabled = true;
			}
			
		
			this.clinicEntity = response.bookingEntity.clinicEntity;
			
			this.patientService.retrieveCurrentBookingQueuePosition(String(this.booking.bookingId), String(this.clinicEntity.clinicId)).subscribe(
				response => {
		
					this.queueNum = response.queueNumber
				}
			)
		}
	);
 }
	async ionViewWillEnter() {

		this.checkPatient();
		// console.log(Object.values(this.sessionService.getCurrentPatient()));
		// this.patientService.retrieveCurrentBooking(Object.values(this.sessionService.getCurrentPatient())[1]).subscribe(
		// 	response => {
		// 		this.booking = response.bookingEntity;
		// 		this.clinicEntity = response.bookingEntity.clinicEntity;
		
		// 	}
		// )		
		// this.patientService.retrieveCurrentBookingQueuePosition(String(this.booking.bookingId), String(this.clinicEntity.clinicId)).subscribe(
		// 	response => {
		// console.log("rub");
		// 		console.log("dsdsd" + response)
		// 		this.queueNum = response.queueNumber
		// 	}
		// )
	}

	async checkin() {


		await this.bookingService.checkin(String(this.booking.bookingId)).subscribe(
			response => {
				console.log(response);
								alert("Checked-in!")
			},
			error => {
				console.log(error);
			}
		)

		
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
				let payment = new PayPalPayment(this.clinicEntity.unitPrice, 'USD', this.booking.visitReason, 'sale');
				this.payPal.renderSinglePaymentUI(payment).then((response) => {

					alert("run");
				 this.bookingService.makePayment(this.booking.bookingId).subscribe(
						response => {
						
											alert("Checked-in!")
						},
						error => {
							alert(error);
						}
					)

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
					alert(error);
					// Error or render dialog closed without being successful
				});
			}, (error) => {
				alert(error);
				// Error in configuration
			});
		}, (error) => {
			alert(error);
			// Error in initialization, maybe PayPal isn't supported or something else
		});
	}

}
