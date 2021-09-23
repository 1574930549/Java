# -*- coding: utf-8 -*-
import urllib.request
import urllib.error
import re
import json
import time
import threading
import redis

# redis连接池
pool = redis.ConnectionPool(host='127.0.0.1', port=6379)


# 抓取页面
def open_url(url, headers):
    try:
        user_agent = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36'
        headers['User-Agent'] = user_agent
        req = urllib.request.Request(url)
        req.headers = headers
        response = urllib.request.urlopen(req, timeout=5)
        html = response.read()
        # html = html.decode('utf-8')
        time.sleep(0.5)
        return html
    except urllib.error.HTTPError:
        pass
    except urllib.error.URLError:
        pass


# 过滤
def html_filter(r, url):
    html = open_url(url, {})
    if html:
        html = html.decode('utf-8')
        data = re.search(r, html)
        data = data.group()
        # print(data)
        return data


# 使用找到的api获取数据
def api_data(api):
    data_list = {}
    title_list = []
    author_list = []
    url_list = []
    headers = {
        'accept': 'application/json, text/javascript, */*; q=0.01',
        'accept-encoding': 'gzip, deflate, br',
        'accept-language': 'zh-CN,zh;q=0.9',
        'cookie':' uuid_tt_dd=10_19746988190-1584499371233-368278; dc_session_id=10_1584499371233.841338; __gads=ID=64af1421f089a255:T=1584499372:S=ALNI_MYw6Ucp6PCNK0nCkRO2DGONW5bg6w; UM_distinctid=170f7a0e8f2c3-0eae63bef2be34-6701b35-1fa400-170f7a0e8f38d0; CNZZDATA1259587897=422564433-1584701663-https%253A%252F%252Fblog.csdn.net%252F%7C1584701663; __yadk_uid=QGabPrlYHqbx6iDSwJr1LPZVohW73kul; UserName=qq_42740897; UserInfo=05477fdaf95d49d7b831c7f04d763087; UserToken=05477fdaf95d49d7b831c7f04d763087; UserNick=qq_42740897; AU=92D; UN=qq_42740897; BT=1586492438121; p_uid=U000000; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_19746988190-1584499371233-368278!5744*1*qq_42740897; Hm_ct_0f88d92387fa53c4333773a435054082=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_ct_e5ef47b9f471504959267fd614d579cd=6525*1*10_19746988190-1584499371233-368278!5744*1*qq_42740897; Hm_ct_feacd7cde2017fd3b499802fc6a6dbb4=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_ct_62052699443da77047734994abbaed1b=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_lvt_feacd7cde2017fd3b499802fc6a6dbb4=1588562275,1588578791; Hm_lvt_62052699443da77047734994abbaed1b=1588562690,1588579140,1588582107; Hm_lvt_0f88d92387fa53c4333773a435054082=1588644950,1588656724,1588665127,1588665604; Hm_up_6bcd52f51e9b3dce32bec4a3997715ac=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%7D; Hm_up_36ef95b506f44e523b4ffc06568ddd6b=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%7D; Hm_ct_36ef95b506f44e523b4ffc06568ddd6b=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_lvt_36ef95b506f44e523b4ffc06568ddd6b=1589335272,1589335863; Hm_lvt_d5a4bd195dfaab300bec76afba840c4e=1589463672; Hm_up_d5a4bd195dfaab300bec76afba840c4e=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%7D; Hm_ct_d5a4bd195dfaab300bec76afba840c4e=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_up_146e5663e755281a5bbe1f3f1c477685=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%7D; Hm_ct_146e5663e755281a5bbe1f3f1c477685=5744*1*qq_42740897!6525*1*10_19746988190-1584499371233-368278; Hm_lvt_146e5663e755281a5bbe1f3f1c477685=1589773133,1589773260,1589773947; searchHistoryArray=%255B%2522opencv%2522%252C%2522c%25E8%25AF%25AD%25E8%25A8%2580%2522%255D; Hm_up_e5ef47b9f471504959267fd614d579cd=%7B%22islogin%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isonline%22%3A%7B%22value%22%3A%221%22%2C%22scope%22%3A1%7D%2C%22isvip%22%3A%7B%22value%22%3A%220%22%2C%22scope%22%3A1%7D%7D; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1589869464,1589869557,1589869649,1589869880; Hm_lvt_e5ef47b9f471504959267fd614d579cd=1588589444,1589785244,1589810343,1589869880; dc_sid=79033914fabf449361c3c83a193713bf; TY_SESSION_ID=41157f7e-cb62-4898-9e67-e62e443bb2f0; c_first_ref=default; c_first_page=https%3A//blog.csdn.net/nav/cloud; dc_tos=qaklcj; announcement=%257B%2522isLogin%2522%253Atrue%252C%2522announcementUrl%2522%253A%2522https%253A%252F%252Fbss.csdn.net%252Fm%252Ftopic%252Flive_recruit%253Futm_source%253Dannounce0515%2522%252C%2522announcementCount%2522%253A0%252C%2522announcementExpire%2522%253A3600000%257D'
        , 'referer': 'https://blog.csdn.net/nav/'+api
    }
    time_stamp = str(int(time.time()*10000000))
    api_url = 'https://www.csdn.net/api/articles?type=more&category='+api+'&shown_offset='+time_stamp
    json_data = open_url(api_url, headers)
    if json_data:
        j_data = json.loads(json_data)
        # jf = json.dumps(j_data, indent=4, ensure_ascii=False)
        # print(jf)
        for jd in j_data['articles']:
            url = jd['url']
            if url.find('edu') < 0:
                title_list.append(jd['title'])
                author_list.append(jd['nickname'])
                url_list.append(jd['url'])
            else:
                continue
            data_list['title'] = title_list
            data_list['author'] = author_list
            data_list['url'] = url_list
            # print(data_list)
        return data_list


# 保存数据
def save_data(func):
    def save(api, times):
        red = redis.Redis(connection_pool=pool)
        func(api, times, red)
        red.close()
    return save


# 爬虫
@save_data
def web_crawler(api, times, red):
    num = 0
    while num < times:
        try:
            data_list = api_data(api)
            if data_list:
                data_len = len(data_list['title'])
                for j in range(data_len):
                    title = data_list['title'][j]
                    author = data_list['author'][j]
                    url = data_list['url'][j]
                    date = html_filter(r'\d\d\d\d-\d\d-\d\d \d\d:\d\d:\d\d', url)
                    if date and not red.exists(url):
                        data = {'Title': title, 'Author': author, 'Date': date}
                        print(data)
                        json_str = json.dumps(data, ensure_ascii=False)
                        red.sadd('urls', url)
                        red.set(name=url, value=json_str)
                    num += 1
                    if num >= times:
                        break
        except IOError:
            continue


# 运行爬虫(爬取threads*times条数据)
def run_crawler(api, threads, times):
    for i in range(threads):
        th = threading.Thread(target=web_crawler, args=(api, times))
        th.start()


if __name__ == '__main__':
    '''
        云计算/大数据：cloud
        动态：watchers
        程序人生：career
        Python：python
        Java：java
        前端：web
    '''
    run_crawler('cloud', 10, 100)

