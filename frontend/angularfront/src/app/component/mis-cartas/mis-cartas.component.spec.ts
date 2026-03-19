/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MisCartasComponent } from './mis-cartas.component';

describe('MisCartasComponent', () => {
  let component: MisCartasComponent;
  let fixture: ComponentFixture<MisCartasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MisCartasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MisCartasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
