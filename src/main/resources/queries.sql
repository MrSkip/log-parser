-- 1. Write MySQL query to find IPs that mode more than a certain number of requests for a given time period

select l.ip, count(*) from logs l
	where l.date between '2017-01-01 00:00:00' and '2017-01-02 00:00:00'
    group by l.ip having count(*) > 500

-- 2. Write MySQL query to find requests made by a given IP

SELECT * FROM test_log.logs where ip='192.168.234.82';