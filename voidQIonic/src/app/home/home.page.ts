import { Component, OnInit, ViewChild } from '@angular/core';
import { IonInfiniteScroll, NavController } from '@ionic/angular';

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

import { ClinicService } from '../clinic.service';
import { SessionService } from '../session.service';
import { Clinic } from '../clinic';

@Component({
	selector: 'app-home',
	templateUrl: 'home.page.html',
	styleUrls: ['home.page.scss'],
})

export class HomePage implements OnInit {
	@ViewChild(IonInfiniteScroll) infiniteScroll: IonInfiniteScroll;
	clinics: Clinic[];
	clinicsWithQueue: Object[];

	map: GoogleMap;
	loading: any;
	async ngOnInit() {
		// Since ngOnInit() is executed before `deviceready` event,
		// you have to wait the event.

		this.clinicService.retrieveClinics().subscribe(
			clinicResponse => {
				this.clinics = clinicResponse.clinicEntities;

				for (let clinic of this.clinics) {
					this.clinicService.retrieveCurrentClinicCurrentDayCurrentQueue(String(clinic.clinicId)).subscribe(
						queueResponse => {
							this.clinicsWithQueue = [];
							this.clinicsWithQueue.push({
								clinicId: clinic.clinicId,
								address: clinic.address,
								clinicName: clinic.clinicName,
								applicationStatus: clinic.applicationStatus,
								description: clinic.description,
								lat: clinic.lat,
								lng: clinic.lng,
								phoneNum: clinic.phoneNum,
								unitPrice: clinic.unitPrice,
								queueNum: queueResponse.queueNumber
							})
							console.log(this.clinicsWithQueue);
						}
					)
				}
			},
			error => {
				console.log('********** home.page.ts (retrieveClinics): ' + error);
			}
		);

		await this.platform.ready();
		await this.loadMap();
	}

	constructor(public sessionService: SessionService, public toastCtrl: ToastController,
		private platform: Platform, private clinicService: ClinicService, private navigationCtrl: NavController) {
	}

	goBook(clinic:Object) {
		console.log(clinic);
		this.sessionService.setClinicObj(clinic);
		this.navigationCtrl.navigateForward("/clinic-details");
	}

	// this is to retrieve and load all clinics for infinite scrolling
	loadData(event) {
		setTimeout(() => {
			console.log('Done');

			this.clinicService.retrieveClinics().subscribe(
				response => {
					this.clinics = response.clinicEntities;
					for (let clinic of this.clinics) {					

						this.clinicService.retrieveCurrentClinicCurrentDayCurrentQueue(String(clinic.clinicId)).subscribe(
							queueResponse => {
								this.clinicsWithQueue.push({
									clinicId: clinic.clinicId,
									address: clinic.address,
									clinicName: clinic.clinicName,
									applicationStatus: clinic.applicationStatus,
									description: clinic.description,
									lat: clinic.lat,
									lng: clinic.lng,
									phoneNum: clinic.phoneNum,
									unitPrice: clinic.unitPrice,
									queueNum: queueResponse.queueNumber
								})
								console.log(this.clinicsWithQueue);
							}
						)
					}
				},
				error => {
					console.log('********** home.page.ts (retrieveClinics): ' + error);
				}
			);
      event.target.disabled = true;
			event.target.complete();
		}, 500);
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

	goToMyLocation() {
		this.map.clear();

		// Get the location of you
		this.map.getMyLocation().then((location: MyLocation) => {
			console.log(JSON.stringify(location, null, 2));

			// Move the map camera to the location with animation
			this.map.animateCamera({
				target: location.latLng,
				zoom: 14,
				duration: 1000
			});

			//add a marker
			for (let clinic of this.clinics) {
				var clinicLocations =
				{
					"lat": Number(clinic.lat),
					"lng": Number(clinic.lng)

				};

				let marker: Marker = this.map.addMarkerSync({
					title: String(clinic.clinicName),
					snippet: String(this.distance(location.latLng.lat,location.latLng.lng,clinicLocations.lat,clinicLocations.lng) + " km away"),
					position: clinicLocations,
					animation: GoogleMapsAnimation.DROP
				});


				//show the infoWindow
				marker.showInfoWindow();
			}

			this.map.on(GoogleMapsEvent.MAP_READY).subscribe(
				(data) => {
					console.log("Click MAP", data);
				}
			);
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


	distance(lat1, lon1, lat2, lon2) {
		var p = 0.017453292519943295;    // Math.PI / 180
		var c = Math.cos;
		var a = 0.5 - c((lat2 - lat1) * p)/2 + 
			c(lat1 * p) * c(lat2 * p) * 
			(1 - c((lon2 - lon1) * p))/2;
		 
		return (12742 * Math.asin(Math.sqrt(a))).toFixed(2); // 2 * R; R = 6371 km
		 }





}
