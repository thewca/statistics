import { TestBed } from '@angular/core/testing';

import { StatisticsApiService } from './statistics-api.service';

describe('StatisticsApiService', () => {
  let service: StatisticsApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatisticsApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
