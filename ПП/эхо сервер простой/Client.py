import socket


class MyExit(Exception):
    pass


sock = socket.socket()
server = input("Введите адрес сервера: ")
port = input("Введите порт сервера: ")

if server == '':
    server = '127.0.0.1'
if port == '':
    port = 9090

try:
    sock.connect((server, int(port)))

    print('Server IP >'+str(server)+' Port >'+str(port))
    host = sock.getsockname()
    print('client IP >'+str(host[0])+' Port >'+str(host[1]))


except ConnectionRefusedError as c:
    print(c)
    print("В соединении отказано!")
    exit()


print("Введите данные: ")

while True:

    try:
        promt = input()
    except KeyboardInterrupt as k:
        print(k)
        print("Остановка программы!")
        exit()

    try:
        result = sock.send(promt.encode())
        if not result:
            raise Exception("Нет данных!")
    except Exception as e:
        print(e)
        exit()

    try:
        data = sock.recv(1024).decode("utf8")
        if (len(data) == 0):
            raise Exception("Нет данных или потеря связи!")
        if ('exit' or 'sstop') in data.lower():
            raise MyExit("Конец связи!")

    except ConnectionResetError as e:
        print(e)
        print("Потеряно соединение с сервером!")
        sock.close()
        exit()

    except MyExit as ex:
        print(ex)
        exit()

    except Exception as s:
        print(s)
        sock.close()
        exit()

    print(data)

sock.close()