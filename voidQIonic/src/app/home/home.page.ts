import { Component, OnInit } from '@angular/core';

import { NavController } from '@ionic/angular';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { PatientService } from '../patient.service';
import { Patient } from '../patient';

@Component({
	selector: 'app-home',
	templateUrl: 'home.page.html',
	styleUrls: ['home.page.scss'],
})

export class HomePage implements OnInit {

	submitted: boolean;
	username: string;
	password: string;
	loginError: boolean;
	errorMessage: string;

	constructor(private router: Router,
		public sessionService: SessionService,
		private patientService: PatientService,
		private navCtrl:NavController) {
		this.submitted = false;
	}

	ngOnInit() {
	}

	patientLogin(patientLoginForm: NgForm) {
		this.submitted = true;
		if (patientLoginForm.valid) {
			this.sessionService.setUsername(this.username);
			this.sessionService.setPassword(this.password);

			this.patientService.patientLogin(this.username, this.password).subscribe(
				response => {
					let patient: Patient = response.patientEntity;

					if (patient != null) {
						this.sessionService.setIsLogin(true);
						this.sessionService.setCurrentPatient(patient);
						this.loginError = false;
						this.navCtrl.navigateForward('appointments');
					}
					else {
						this.loginError = true;
					}
				},
				error => {
					this.loginError = true;
					this.errorMessage = error;
				}
			);
		}
	}
}
