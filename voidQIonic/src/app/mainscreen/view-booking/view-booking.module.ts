import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { ViewBookingPage } from './view-booking.page';
import { PayPal } from '@ionic-native/paypal/ngx';

const routes: Routes = [
  {
    path: '',
    component: ViewBookingPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  providers: [
	  PayPal
  ],
  declarations: [ViewBookingPage]
})
export class ViewBookingPageModule {}
