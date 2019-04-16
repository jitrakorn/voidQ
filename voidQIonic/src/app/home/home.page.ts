// import { Component, OnInit } from '@angular/core';

// import { Router } from '@angular/router';
// import { NgForm } from '@angular/forms';

// import { SessionService } from '../session.service';
// import { PatientService } from '../patient.service';
// import { Patient } from '../patient';

// @Component({
// 	selector: 'app-home',
// 	templateUrl: 'home.page.html',
// 	styleUrls: ['home.page.scss'],
// })

// export class HomePage implements OnInit {

// 	submitted: boolean;
// 	username: string;
// 	password: string;
// 	loginError: boolean;
// 	errorMessage: string;

// 	constructor(private router: Router,
// 		public sessionService: SessionService,
// 		private patientService: PatientService) {
// 		this.submitted = false;
// 	}

// 	ngOnInit() {
// 	}

// 	patientLogin(patientLoginForm: NgForm) {
// 		this.submitted = true;
// 		if (patientLoginForm.valid) {
// 			this.sessionService.setUsername(this.username);
// 			this.sessionService.setPassword(this.password);

// 			this.patientService.patientLogin(this.username, this.password).subscribe(
// 				response => {
// 					let patient: Patient = response.patientEntity;

// 					if (patient != null) {
// 						this.sessionService.setIsLogin(true);
// 						this.sessionService.setCurrentPatient(patient);
// 						this.loginError = false;
// 						window.location.reload();
// 					}
// 					else {
// 						this.loginError = true;
// 					}
// 				},
// 				error => {
// 					this.loginError = true;
// 					this.errorMessage = error;
// 				}
// 			);
// 		}
// 	}
// }

import { Component, OnInit } from '@angular/core';
import {
  ToastController,
  Platform
} from '@ionic/angular';
import {
  GoogleMaps,
  GoogleMap,
  GoogleMapsEvent,
  Marker,
  GoogleMapsAnimation,
  MyLocation
} from '@ionic-native/google-maps/';

import { SessionService } from '../session.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})

export class HomePage implements OnInit
{
  
	map: GoogleMap;
	loading: any;
  async ngOnInit() {
    // Since ngOnInit() is executed before `deviceready` event,
		// you have to wait the event.
		
	


    this.platform.ready();
    this.loadMap();
  }

	 
	constructor(public sessionService: SessionService,public toastCtrl: ToastController,
		private platform: Platform)
	{
	}
  loadMap() {
		this.map = GoogleMaps.create('map_canvas', {
		  // camera: {
		  //   target: {
		  //     lat: 43.0741704,
		  //     lng: -89.3809802
		  //   },
		  //   zoom: 18,
		  //   tilt: 30
		  // }
		});
		this.goToMyLocation();
	  }
	
	
	  goToMyLocation(){
		this.map.clear();


		


	// Get the location of you
	this.map.getMyLocation().then((location: MyLocation) => {
		console.log(JSON.stringify(location, null ,2));

		// Move the map camera to the location with animation
		this.map.animateCamera({
		target: location.latLng,
		zoom: 14,
		duration: 1000
		});
		
			

		})
		.catch(err => {
		  //this.loading.dismiss();
		  this.showToast(err.error_message);
		});
	  }
	
	
		async showToast(message: string) {
			let toast = await this.toastCtrl.create({
				message: message,
				duration: 2000,
				position: 'middle'
			});
			toast.present();
			}



		

			// goToMyLocation(){
			// 	this.map.clear();
		
		
			// 	this.clinicService.retrieveClinics().subscribe(
			// 		response => {
			// 			this.clinics = response.clinicEntities;
			// 			console.log(this.clinics);
			// 		},
			// 		error => {
			// 			console.log('**** homepage.ts: ' + error);
			// 		}
			// 	);
		
			
			// 	// Get the location of you
			// 	this.map.getMyLocation().then((location: MyLocation) => {
			// 		console.log(JSON.stringify(location, null ,2));
			
			// 		// Move the map camera to the location with animation
			// 		this.map.animateCamera({
			// 		target: location.latLng,
			// 		zoom: 14,
			// 		duration: 1000
			// 		});
			
			// 		//add a marker
			// 		let marker: Marker = this.map.addMarkerSync({
			// 		title: 'HealthWay Clinic!',
			// 		snippet: '3km away',
			// 		position: location.latLng,
			// 		animation: GoogleMapsAnimation.DROP
			// 		});
			
			// 		this.map.
			// 		//show the infoWindow
			// 		marker.showInfoWindow();
			
				
			// 		this.map.on(GoogleMapsEvent.MAP_READY).subscribe(
			// 		(data) => {
			// 			console.log("Click MAP",data);
			// 		}
			// 		);
			// 	})
			// 	.catch(err => {
			// 		//this.loading.dismiss();
			// 		this.showToast(err.error_message);
			// 	});
			// 	}
			
			// 	async showToast(message: string) {
			// 	let toast = await this.toastCtrl.create({
			// 		message: message,
			// 		duration: 2000,
			// 		position: 'middle'
			// 	});
			// 	toast.present();
			// 	}
			
	

	
	 
	
	}