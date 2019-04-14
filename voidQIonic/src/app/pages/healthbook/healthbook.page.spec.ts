import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HealthbookPage } from './healthbook.page';

describe('HealthbookPage', () => {
  let component: HealthbookPage;
  let fixture: ComponentFixture<HealthbookPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HealthbookPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HealthbookPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
