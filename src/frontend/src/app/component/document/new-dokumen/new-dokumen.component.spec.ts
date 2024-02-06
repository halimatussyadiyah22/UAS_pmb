import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewDokumenComponent } from './new-dokumen.component';

describe('NewDokumenComponent', () => {
  let component: NewDokumenComponent;
  let fixture: ComponentFixture<NewDokumenComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NewDokumenComponent]
    });
    fixture = TestBed.createComponent(NewDokumenComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
