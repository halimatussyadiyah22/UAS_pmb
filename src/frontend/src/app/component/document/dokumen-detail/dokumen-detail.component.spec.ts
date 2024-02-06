import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumenDetailComponent } from './dokumen-detail.component';

describe('DokumenDetailComponent', () => {
  let component: DokumenDetailComponent;
  let fixture: ComponentFixture<DokumenDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DokumenDetailComponent]
    });
    fixture = TestBed.createComponent(DokumenDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
