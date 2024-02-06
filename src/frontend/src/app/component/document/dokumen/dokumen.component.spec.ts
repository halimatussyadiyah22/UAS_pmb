import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DokumenComponent } from './dokumen.component';

describe('DokumenComponent', () => {
  let component: DokumenComponent;
  let fixture: ComponentFixture<DokumenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DokumenComponent]
    });
    fixture = TestBed.createComponent(DokumenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
