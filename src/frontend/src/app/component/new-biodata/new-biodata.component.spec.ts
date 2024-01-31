import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewBiodataComponent } from './new-biodata.component';

describe('NewBiodataComponent', () => {
  let component: NewBiodataComponent;
  let fixture: ComponentFixture<NewBiodataComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewBiodataComponent]
    });
    fixture = TestBed.createComponent(NewBiodataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
