import requests
from lxml import html

login_url = 'https://frm-cvd-ca.esolg.ca/Township-of-King/Screening-Form'

def main():
    session_requests = requests.session()
    result = session_requests.get(login_url)
    tree = html.fromstring(result.text)
    veri_token = tree.xpath("/html/body/div[1]/div/div/form/input[1]/@value")[0]
    print(veri_token)

    headerpayload = {
        'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36',
        'X-Requested-With': 'XMLHttpRequest',
        'Host': 'frm-cvd-ca.esolg.ca',
        'Origin': 'https://frm-cvd-ca.esolg.ca',
        'Referer': login_url,
        '__RequestVerificationToken': veri_token
    }

    payload = {
        "__RequestVerificationToken": veri_token,
        "Q_b9849eb2-813d-4d0a-a1ce-643f1c8af986_0": "arian nasr",
        "Q_62ebd8c8-7d45-481d-a8b1-ad54a390a029_0": "anasr@my.yorku.ca",
        "Q_f4a3bb2e-ebab-4660-a9a1-75c0d79fe0b4_0": "",
        "Q_20cd46d1-3b95-46ad-81a3-b7b4d7fe7bf9_0": "b9558536-87d3-4395-aceb-ac3e012b4bad",
        "Q_012d8ddf-e75a-4266-ae78-f59502862aa9_0": "Trisan Centre",
        "Q_6b61b457-f325-41d7-9784-5cb4a959223f_0": "All Areas",
        "Q_301151e9-02c5-48b6-ae09-4b605bcbc99f_0": "85b25799-c250-44a0-a23f-4676ac322159",
        "Q_eb129e60-dce7-438b-9666-bb92112c1c92_0": "f9474264-4bd1-4696-9ef9-ed5eae3cb617",
        "Q_dac07f48-d9e0-4395-9b3b-6c288d5bec1f_0": "4e6aa5ab-b058-4b8d-a8b7-05d229d57c16",
        "Q_972f65e9-bff1-4528-8560-a4d1da5d325d_0": "",
        "FormId": "276caa59-80a5-4ced-9b9d-025e1d753b4a",
        "_ACTION": "Continue",
        "PageIndex": "1"
    }

    result = session_requests.post(
    login_url,
    data=payload,
    headers=headerpayload
    )

    print(result.status_code)

# Entry point
if __name__ == '__main__':
    main()