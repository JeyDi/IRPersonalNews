import os
import yaml


def saveArticleDisk(title, article_text, paper, name, folder=None, path=None):
    """
    Save some articles to disk in a txt form
    """
    # Set the path for the file
    base_path = os.path.abspath('')
    base_path = os.path.join(base_path, "downloaded_articles")

    if path is not None:
        file_path = path
    else:
        file_path = base_path

    file_name = paper + "-" + name + '.txt'

    if folder is not None:
        export_path = os.path.join(file_path, folder, file_name)
    else:
        export_path = os.path.join(file_path, file_name)

    # Write the file
    try:
        file = open(export_path, "w+")
        file.writelines([title, "\n", article_text])
        file.close()

        return True

    except Exception as message:

        print(f"Impossible to write the file: {export_path} because: {message} ")

        return False


def readYMLFile(path=None, filename='config.yml'):
    """
    Read a YAML file with some info's
    INPUT: path and filename
    OUTPUT: things read
    """
    # Set the path for the file
    base_path = os.path.abspath('')

    if path is not None:
        file_path = path
    else:
        file_path = base_path

    # Generate the path
    file_path = os.path.join(file_path, filename)

    try:
        with open(file_path, 'r') as file:
            configuration = yaml.full_load(file)
        file.close()
        print(f'File successfully read from {file_path}')
        return configuration

    except Exception as message:
        print(f"Impossible write the file: {file_path}, please retry : {message}")
        return False


def writeYMLFile(data_write, path=None, filename='config.yml'):
    """
    Write a new YAML file with some data to write
    """
    # Set the path for the file
    base_path = os.path.abspath('')

    if path is not None:
        file_path = path
    else:
        file_path = base_path

    file_path = os.path.join(file_path, filename)

    try:
        with open(file_path, 'w') as file:
            configuration = yaml.dump(data_write, file)
        file.close()
        print(f'File successfully write to {file_path}')
        return configuration

    except Exception as message:
        print(f"Impossible write the file: {file_path}, please retry : {message}")
        return False


def saveCacheObjectDisk():
    """
    TODO
    Save a python object (like dictionary, list, ...) to disk [cache]
    """

    return True


def readCacheObjectDisk():
    """
    TODO
    Read a python object (like dictionary, list, ...) from disk and import in python [cache]
    """

    return True

