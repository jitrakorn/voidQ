import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { TabsPage } from './tabs.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsPage,
    children: [
      { path: 'clinics', loadChildren: '../clinics/clinics.module#ClinicsPageModule' },
      { path: 'profile', loadChildren: '../profile/profile.module#ProfilePageModule' },
  { path: 'view-booking', loadChildren: '../view-booking/view-booking.module#ViewBookingPageModule' }
    ]
  },
  {
    path: '',
    redirectTo:'tabs/clinics',
    pathMatch : 'full'
  }
  
];



@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [TabsPage]
})
export class TabsPageModule {}
