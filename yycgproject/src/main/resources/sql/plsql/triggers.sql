create or replace trigger yyjsdmx2014_insert
  after insert
  on yyjsdmx2014
  for each row
  declare begin update yybusiness2014 t
  set t.yyjsdid = :new.id, t.jsl = :new.jsl, t.jszt = :new.jszt, t.jsje = :new.jsje
  where t.yycgdid = :new.yycgdid and t.ypxxid = :new.ypxxid;
  end yyjsdmx2014_insert;
