import socket
from nameservice import NameService
from client_thread import client_thread
import json

config_file = "conf.json"

with open(config_file) as f:
	conf = json.load(f)

HOST_ADDRESS = conf['HOST']
PORT = conf['PORT']
BUFFER_SIZE = 1024

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST_ADDRESS, PORT))
s.listen(1)

nameservice = NameService()

while 1:
    conn, addr = s.accept()
    ct = client_thread(conn, nameservice)
    ct.run()
            
