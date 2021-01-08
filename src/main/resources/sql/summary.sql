login:
input: name,password
select password as upassword from doctor where username = name

initial doctor:
intput: doctorName
select area from doctor natural join treatment_area as doctor_area where name = doctorName


moving
find if patient in response_area can leave:
select username from patient where here treatmentArea = response_area and norm_temp_days>=3 and norm_nuc_days>=2
create recent_temp as select top 3 temprature from daily_status_record ORDER BY AddDateTime DESC 获取最近三次体温
int qualified = select count(*) from recent_temp where temperature <37.3
判断qualified是否=3
获取当前时间减去24小时的startDate
int all = select  count(*)  from daily_status_record  where date >startDate
int negative = select  count(*)  from daily_status_record  where date >startDate  and ucleic_acid_test_result = 0
判断all 是否等于negative