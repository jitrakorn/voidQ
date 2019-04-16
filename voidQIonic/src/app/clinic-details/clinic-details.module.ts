import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { ClinicDetailsPage } from './clinic-details.page';

const routes: Routes = [
  {
    path: '',
    component: ClinicDetailsPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [ClinicDetailsPage]
})
export class ClinicDetailsPageModule {}
