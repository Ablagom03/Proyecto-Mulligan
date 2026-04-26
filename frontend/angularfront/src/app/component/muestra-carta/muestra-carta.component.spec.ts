/* tslint:disable:no-unused-variable */
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';

import { MuestraCartaComponent } from './muestra-carta.component';

describe('MuestraCartaComponent', () => {
  let component: MuestraCartaComponent;
  let fixture: ComponentFixture<MuestraCartaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MuestraCartaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MuestraCartaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
