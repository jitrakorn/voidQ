import { Component, OnInit, ViewChild } from '@angular/core';
import { IonInfiniteScroll } from '@ionic/angular';

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

	map: GoogleMap;
	loading: any;
	async ngOnInit() {
		// Since ngOnInit() is executed before `deviceready` event,
		// you have to wait the event.

		this.clinicService.retrieveClinics().subscribe(
			response => {
				this.clinics = response.clinicEntities;
			},
			error => {
				console.log('********** home.page.ts (retrieveClinics): ' + error);
			}
		);


		await this.platform.ready();
		await this.loadMap();
	}

	constructor(public sessionService: SessionService, public toastCtrl: ToastController,
		private platform: Platform, private clinicService: ClinicService) {
	}

	// this is to retrieve and load all clinics for infinite scrolling
	loadData(event) {
		setTimeout(() => {
			console.log('Done');

			this.clinicService.retrieveClinics().subscribe(
				response => {
					this.clinics = response.clinicEntities;
					console.log(this.clinics);
				},
				error => {
					console.log('********** home.page.ts (retrieveClinics): ' + error);
				}
			);
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
					snippet: '3km away',
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








}
