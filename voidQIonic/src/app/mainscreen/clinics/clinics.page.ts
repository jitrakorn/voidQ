import { Component, OnInit, ViewChild , NgZone} from '@angular/core';
import { IonInfiniteScroll, ToastController, Platform, NavController } from '@ionic/angular';

import { GoogleMap, GoogleMaps, MyLocation, Marker, GoogleMapsAnimation, GoogleMapsEvent } from '@ionic-native/google-maps/';
import { Clinic } from 'src/app/clinic';
import { ClinicService } from 'src/app/clinic.service';
import { SessionService } from 'src/app/session.service';
import { CompileShallowModuleMetadata } from '@angular/compiler';
import { Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-clinics',
  templateUrl: './clinics.page.html',
  styleUrls: ['./clinics.page.scss'],
})
export class ClinicsPage implements OnInit {


	clinics: Clinic[];
	clinicsWithQueue: Object[];
	

	map: GoogleMap;
	loading: any;
	async ngOnInit() {
	
	
		
	}
	
	async ionViewWillEnter() {
	

		this.clinicsWithQueue= [];
		
		 this.clinicService.retrieveClinics().subscribe(
			clinicResponse => {
				this.zone.run(() => {
					this.clinics = clinicResponse.clinicEntities;
				console.log(this.clinics);

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
				});
				
			},
			error => {
				console.log('********** home.page.ts (retrieveClinics): ' + error);
			}
		);
		

		await this.platform.ready().then( () => {

	
			this.loadMap();
			this.map.one( GoogleMapsEvent.MAP_READY ).then( ( data: any ) => {
				this.goToMyLocation();
			})
		});

	
	
	}
	

	constructor(public sessionService: SessionService, public toastCtrl: ToastController,
		private platform: Platform, private clinicService: ClinicService, private navigationCtrl: NavController,private router: Router,private location : Location,private zone : NgZone) {
			this.clinicsWithQueue = [];
			
	}

	// loadData(event) {
	// 	setTimeout(() => {
	// 		console.log('Done');

	// 		this.clinicService.retrieveClinics().subscribe(
	// 			response => {
	// 				this.clinics = response.clinicEntities;
	// 				for (let clinic of this.clinics) {

	// 					this.clinicService.retrieveCurrentClinicCurrentDayCurrentQueue(String(clinic.clinicId)).subscribe(
	// 						queueResponse => {
	// 							this.clinicsWithQueue.push({
	// 								clinicId: clinic.clinicId,
	// 								address: clinic.address,
	// 								clinicName: clinic.clinicName,
	// 								applicationStatus: clinic.applicationStatus,
	// 								description: clinic.description,
	// 								lat: clinic.lat,
	// 								lng: clinic.lng,
	// 								phoneNum: clinic.phoneNum,
	// 								unitPrice: clinic.unitPrice,
	// 								queueNum: queueResponse.queueNumber
	// 							})
	// 							console.log(this.clinicsWithQueue);
	// 						}
	// 					)
	// 				}
	// 			},
	// 			error => {
	// 				console.log('**** home.page.ts (retrieveClinics): ' + error);
	// 			}
	// 		);
	// 		event.target.disabled = true;
	// 		event.target.complete();
	// 	}, 500);
	// }

	goBook(clinic: Object) {
		console.log(clinic);
		this.sessionService.setClinicObj(clinic);
		//this.router.navigate(['/clinic-details']);
		this.navigationCtrl.navigateRoot("/clinic-details");
	}

	loadMap() {
		this.map = GoogleMaps.create('map_canvas', {
			controls: {
				'compass': true,
				'myLocationButton': true,
				'myLocation': true,   // (blue dot)
				'indoorPicker': true,
				'zoom': true,          // android only
				'mapToolbar': true     // android only
			  }
		});
	
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
			for (let clinic of this.clinicsWithQueue) {
				var clinicLocations =
				{
					"lat": Number(clinic["lat"]),
					"lng": Number(clinic["lng"])

				};

				let marker: Marker = this.map.addMarkerSync({
					title: String(clinic["clinicName"]),
					snippet: String(this.distance(location.latLng.lat, location.latLng.lng, clinicLocations.lat, clinicLocations.lng) + " km away"),
					position: clinicLocations,
					animation: GoogleMapsAnimation.DROP
				});
			
				marker.on(GoogleMapsEvent.INFO_CLICK).subscribe(() => {
					this.goBook(clinic);
					
				  });
		
			}

			this.map.on(GoogleMapsEvent.MAP_READY).subscribe(
				(data) => {
					console.log("Click MAP", data);
				}
			);
		})
			.catch(err => {

			});

	}

	distance(lat1, lon1, lat2, lon2) {
		var p = 0.017453292519943295;    // Math.PI / 180
		var c = Math.cos;
		var a = 0.5 - c((lat2 - lat1) * p) / 2 +
			c(lat1 * p) * c(lat2 * p) *
			(1 - c((lon2 - lon1) * p)) / 2;

		return (12742 * Math.asin(Math.sqrt(a))).toFixed(2); // 2 * R; R = 6371 km
	}


}
