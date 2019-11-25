declare
  num number := 3;
begin
  if mod(num, 2) <> 0
  then
    dbms_output.put_line(num || '是奇数');
  else
    dbms_output.put_line(num || '是偶数');
  end if;
end;