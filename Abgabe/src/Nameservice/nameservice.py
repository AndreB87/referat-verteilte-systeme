class NameService:
    
    def __init__(self):
        self.objects = dict()

    def rebind(self, servant, name):
        self.objects[name] = servant

    def resolve(self, name):
        try:
            return self.objects[name]
        except:
            return None