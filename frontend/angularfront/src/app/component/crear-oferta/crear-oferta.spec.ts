import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearOferta } from './crear-oferta';

describe('CrearOferta', () => {
  let component: CrearOferta;
  let fixture: ComponentFixture<CrearOferta>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearOferta],
    }).compileComponents();

    fixture = TestBed.createComponent(CrearOferta);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
