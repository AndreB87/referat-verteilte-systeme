from _thread import start_new_thread

class client_thread:
    def __init__(self, conn, nameservice):
        self.nameservice = nameservice
        self.conn = conn

    def run(self):
        start_new_thread(self.listen,())

    def listen(self):
        alive = True
        while alive:
            try:	
                data = self.conn.recv(1024).decode()
                print(type(data))
                print(data)
                message_parts = data.split('!')
                result = ""
                if message_parts[0] == "rebind":
                    result = self.rebind(message_parts[1])
                elif message_parts[0] == "resolve":
                    result = self.resolve(message_parts[1])
                else:
                    result = "nok:unknown message"
                self.send(result)
            except:
                print("Verbindung unterbrochen")
                self.conn.close()
                alive = False

    def send(self, message):
        print("Send message: ", message)
        message = message + "\n"
        self.conn.send(message.encode())

    def rebind(self, message):
        object_parts = message.split(':')
        name = object_parts[0]
        o = {
                'host' : object_parts[1],
                'port' : object_parts[2]
            }
        self.nameservice.rebind(o, name)
        return "ok"
        
    def resolve(self, name):
        try:
            o = self.nameservice.resolve(name)
            result = "ok:{0}:{1}:{2}".format(name, o['host'], o['port'])  
        except:
            result = "nok:no object"
        return result
