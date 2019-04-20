import { Component, OnInit } from '@angular/core';
import { NavController } from '@ionic/angular';
import { SessionService } from '../../session.service';
import { PatientService } from '../../patient.service';
import { Patient } from '../../patient';
import { NgForm } from '@angular/forms';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {
  updateError: boolean;
  updateErrorMessage: string;
  currentPatient: Patient;
  submitted: boolean;
  oldPasswordText: String;
  newPasswordText: String;


  constructor(private navigationCtrl: NavController,
    private patientService: PatientService,
    public sessionService: SessionService) {
    this.submitted = false;
  }

  ngOnInit() {
    this.currentPatient = this.sessionService.getCurrentPatient();

  }
  goToLogout() {
    this.sessionService.setCurrentPatient(null);
    this.sessionService.setIsLogin(null);
    this.sessionService.setClinicObj(null);
    this.navigationCtrl.navigateRoot('/home');

  }

  public buttonClicked: boolean = false;

  changePassword() {
    this.buttonClicked = !this.buttonClicked;
  }
  update(updatePatientForm: NgForm) {
    this.submitted = true;
    if (updatePatientForm.valid) {
      this.patientService.updatePatientDetails(this.currentPatient.email, this.currentPatient.password, this.currentPatient).subscribe(
        response => {
          console.log(response);
          alert('Patient details sucessfully updated!');
        },
        error => {
          this.updateError = true;
          this.updateErrorMessage = error
          console.log("ERROR");
        }
      )
    } else {

    }
  }

  updatePassword(updatePatientPasswordForm: NgForm) {
    this.submitted = true;
    if (updatePatientPasswordForm.valid) {
      console.log(this.oldPasswordText);
      this.patientService.updatePatientPassword(this.oldPasswordText, this.newPasswordText, this.currentPatient).subscribe(
        response => {
          console.log(response);
          alert('Patient password details sucessfully updated!');
        },
        error => {
          this.updateError = true;
          this.updateErrorMessage = error
          alert(this.updateErrorMessage);
          console.log("ERROR");
        }
      )
    } else {

    }
  }
}
