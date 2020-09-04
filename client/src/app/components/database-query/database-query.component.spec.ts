import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatabaseQueryComponent } from './database-query.component';

describe('DatabaseQueryComponent', () => {
  let component: DatabaseQueryComponent;
  let fixture: ComponentFixture<DatabaseQueryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DatabaseQueryComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DatabaseQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
