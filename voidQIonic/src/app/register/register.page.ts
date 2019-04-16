import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

import { SessionService } from '../session.service';
import { PatientService } from '../patient.service';
import { Patient } from '../patient';
import { NavController } from '@ionic/angular';


@Component({
	selector: 'app-register',
	templateUrl: './register.page.html',
	styleUrls: ['./register.page.scss'],
})

export class RegisterPage implements OnInit {

	submitted: boolean;
	newPatient: Patient;
	registerError: boolean;
	registerErrorMessage: string;
	message: string;
	toast: any;

	constructor(private router: Router,
		public sessionService: SessionService,
		private patientService: PatientService,
		private navigationCtrl: NavController) {
		this.submitted = false;
		this.registerError = false;
		this.newPatient = new Patient();
	}

	ngOnInit() {
	}

	clear() {
		this.submitted = false;
		this.newPatient = new Patient();
	}

	patientRegister(patientRegisterForm: NgForm) {
		this.submitted = true;

		if (patientRegisterForm.valid) {

			this.patientService.patientRegister(this.newPatient).subscribe(
				response => {
					let newPatientId: number = response.userId;
					this.message = "Patient " + newPatientId + "successfully registered";
					alert('Patient sucessfully registered!');
				},
				error => {
					this.registerError = true;
					this.registerErrorMessage = error
				}
			);
		}
		else {

		}
	}

	goBack() {
		this.navigationCtrl.navigateBack('/home');
	}
}
