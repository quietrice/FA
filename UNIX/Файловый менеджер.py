# -*- coding: utf-8 -*-

import os
import shutil
import pathlib
import sys


def help(name):
    print("""Список комманд: 
          \n1. mkdir<path>- создает папку или дерево по желаемому пути,
          обрабатывает как абсолютные, так и относительные пути. 
          \n2. cd<optional: path>- изменяет текущую папку.
          Если аргументы не указаны, выполняется переход в предыдущую папку.
          \n3. rmdir<path>- удаляет каталог.
          Можно удалить только из папки, в которой в данный момент находится пользователь. 
          \n4. create<name>- создает файл в папке, в которой находится пользователь.
          \n5. rename<name>- переименовывает файл из папки, в которой находится пользователь.
          Новое имя дается после исполнения. 
          \n6. read<name>- читает файл из текущей папки. 
          \n7. remove<name>- удаляет файл из текущей папки
          \n8. copy<source>- копирует файл / папку из текущего каталога.
          Пункт назначения принимается после выполнения.
          \n9. move<name>- перемещает файл из текущей папки.
          Пункт назначения взят после исполнения.
          \n10. write<name>- записывает в файл в текущем каталоге.
          Текст взят после казни. 
          \n11. печатает содержимое текущей папки. 
          \n12. help- выводит список комманд""")

def input_check():
    return 0

def get_directory():
    try:
        f=open('set.txt','r')
        x=f.read()
        if('path' in x):
            path=x[5::]
        else:
            os.remove('set.txt')
            raise FileNotFoundError
    except FileNotFoundError:
        f=open('set.txt',"x")
        path=input('Введите полный путь к рабочему каталогу \n')
        f.write('path='+path) 
        
    f.close()
    return path

def change_n(name):
    ind=None
    n_name=[]
    if('/' in name):
        ind='/'
    elif("\\" in name):
        ind="\\"
    elif("\\"[-1] in name):
        ind="\\"[-1]
    
    if(ind!=None):
        n_name=name.split(ind)
        
        if(n_name[0]==''):
            n_name.pop(0)
    else:
        n_name.append(name)
    return n_name

def make_path(path):

    if(os.name=='nt' and ":" in path[0]):
        new_path="\\".join(path)
    else:
        new_path=os.path.join(*path)
    

    return new_path

default_path=change_n(get_directory())
path=default_path.copy()
root_dir=path[len(path)-1]

def rel_to_abs(name):
    global path
    if(str(type(name))=="<class 'list'>"):
        name=' '.join(name)
    path.append(name)
    name=make_path(path)
    
    path.pop()
    return name

def root_checker(path):
    if(not root_dir in path or default_path[0:default_path.index(root_dir):]!=path[0:path.index(root_dir):]):
        path=default_path.copy()
    return path




def cd(name):
    global path
    global default_path
    global root_dir
    C=0

    
    if(name[0]==''):
        path.pop()
        path=root_checker(path)
    
    elif(os.path.isdir(name[0]) or name[0] in os.listdir(make_path(path))):
        
        if(root_dir==name[0]):
            path=path[0:path.index(root_dir):]
        elif(len(name)>1): 
            cd(change_n(name[0]))
            name.pop(0)
            
            cd(name)
        else:
            path.extend(name)
            path=root_checker(path)
        C=1
    return C
    



def mkdir(name):
    global path
    
    if(len(name)>1):
        mkdir(change_n(name[0]))
        name.pop(0)
        mkdir(name)
        
    else:
        try:
            path.extend(name)
  
            os.mkdir(make_path(path))
        except:
            print('Невозможно создать каталог. Попробуйте другое местоположение или проверьте корневое местоположение')

    
def rmdir(name):
    path.extend(name)
    
    C=input('Уверен что хочешь удалить '+make_path(path)+' и все его содержание? y/n ')
    if(C=='y'):
        try:
            shutil.rmtree(make_path(path))
            path.pop()
        except:
            print('Невозможно удалить каталог')
    elif(C=='n'):
        print('Ok')
    else:
        print('Неизвестное хначение')


def create(name):
    if(len(name)>1):
        if(cd(name[:-1:])==0):
            mkdir(name[:-1:])
    
    path.append(name[len(name)-1])
   
    try:
        f=open(make_path(path),"x") 
        f.close()
        
    except FileExistsError:
        print('Файл уже существует')
    except PermissionError:
        print('Что-то пошло не так. Проверьте, доступна ли папка только для чтения и есть ли какие-либо папки с похожим именем')
    path.pop()
        
def rename(name):
    global path
    n_name=rel_to_abs(input("Введите новое имя \n"))
    name=rel_to_abs(name)
    os.rename(name,n_name)


def read(name):
    global path
    name=rel_to_abs(name)
    try:
        f=open(name,"r")
        print(f.read())
    except FileNotFoundError:
        print('Файл не существует')

def remove(name):
    global path
    name=rel_to_abs(name)
    try:
        os.remove(name) 
    except FileNotFoundError:
        print('Файл не существует')

def copy(name): 
    global path
    new_fold=path[0:path.index(root_dir):]
    folder_path=change_n(input("Введите полный путь к папке\n"))
    new_fold.extend(folder_path)
    
    if(len(name)>1):
        print('Невозможно найти папку в текущем каталоге')
    else:
        new_fold.extend(name)
        path.extend(name)
        name=path

        try:
             
            folder=root_checker(new_fold)
            
            if(folder!=new_fold):
                print('Невозможно получить доступ к желаемой папке')
            else:
                try:
                    shutil.copytree(make_path(name),make_path(folder))
                except NotADirectoryError:
                    shutil.copy2(make_path(name),make_path(folder))
        except FileNotFoundError:
            print('Файл не существует')
        path.pop()

def move(name):
    copy(name) 
    remove(name)



def write(name):
    global path
    name=rel_to_abs(name)
    try:
        f=open(name,"a")
        txt=input("Введите текст \n")
        f.write(txt)
        f.close() 
    except FileNotFoundError:
        print('Файл не существует')

def ls(name): 
    print(os.listdir(make_path(path)))

comands=[mkdir, cd, rmdir, create, rename, read, remove, copy, move, write, ls, help]

commands={
    'mkdir':mkdir, 
    'cd':cd, 
    'rmdir':rmdir, 
    'create':create, 
    'rename':rename, 
    'read':read, 
    'remove':remove, 
    'copy':copy,
    'move':move,
    'write':write,
    'ls':ls,
    'help':help
    }


while True:

    command=input("\\".join(path[path.index(root_dir)::])+"\n").split()

    try:
        commands[command[0]](change_n(' '.join(command[1::])))
        
    except IndexError:
        commands[command[0]]("")
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        