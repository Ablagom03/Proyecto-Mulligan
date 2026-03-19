/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { CartasService } from './cartas.service';

describe('Service: Cartas', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CartasService]
    });
  });

  it('should ...', inject([CartasService], (service: CartasService) => {
    expect(service).toBeTruthy();
  }));
});
