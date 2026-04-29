/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { ImagenService } from './imagen.service';

describe('Service: Imagen', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ImagenService]
    });
  });

  it('should ...', inject([ImagenService], (service: ImagenService) => {
    expect(service).toBeTruthy();
  }));
});
