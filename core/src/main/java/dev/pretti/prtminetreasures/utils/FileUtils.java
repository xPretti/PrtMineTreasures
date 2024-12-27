package dev.pretti.prtminetreasures.utils;


import dev.pretti.prtminetreasures.enums.EnumFileExistType;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils
{
  /**
   * Retorna o tipo do diretório
   */
  public static EnumFileExistType Exist(String dir)
  {
    if(!dir.isEmpty())
      {
        Path    directory = Paths.get(dir);
        boolean exist     = Files.exists(directory);
        if(exist)
          {
            boolean isDir = Files.isDirectory(directory);
            if(isDir)
              {
                return (EnumFileExistType.IS_DIRECTORY);
              }
            else
              {
                return (EnumFileExistType.IS_FILE);
              }
          }
      }
    return (EnumFileExistType.NOT_EXIST);
  }

  /**
   * Verifica se uma pasta ou arquivoexiste
   */
  public static boolean FolderExist(String folder)
  {
    return (Exist(folder) == EnumFileExistType.IS_DIRECTORY);
  }

  public static boolean FileExist(String file)
  {
    return (Exist(file) == EnumFileExistType.IS_FILE);
  }

  /**
   * Método para criar um arquivo ou pasta
   */
  public static boolean FolderCreate(String folderPath)
  {
    if(!FolderExist(folderPath))
      {
        Path path = Paths.get(folderPath);
        try
          {
            Files.createDirectories(path);
            return true;
          } catch(IOException e)
          {
            System.err.println("Erro ao criar um novo diretório: " + e.getMessage());
            return false;
          }
      }
    return true;
  }

  public static boolean FileCreate(String filePath)
  {
    if(!FileExist(filePath))
      {
        Path path      = Paths.get(filePath);
        Path parentDir = path.getParent();
        if(FolderCreate(parentDir.toString()))
          {
            try
              {
                Files.createFile(path);
                return true;
              } catch(IOException e)
              {
                System.err.println("Erro ao criar um novo arquivo: " + e.getMessage());
                return false;
              }
          }
      }
    return true;
  }

  /**
   * Método de retorno de arquivos
   */
  public static File getFile(String filePath)
  {
    if(FileExist(filePath))
      {
        return new File(filePath);
      }
    return null;
  }

  /**
   * Método para deletar uma pasta ou arquivo
   */
  public static boolean FolderClear(Path path)
  {
    if(Files.isDirectory(path))
      {
        try
          {
            boolean               success = true;
            DirectoryStream<Path> stream  = Files.newDirectoryStream(path);
            for(Path entry : stream)
              {
                if(!FolderClear(entry))
                  {
                    success = false;
                  }
              }
            return success;
          } catch(IOException e)
          {
            System.err.println("Erro ao criar um novo diretório stream: " + e.getMessage());
            return false;
          }
      }
    try
      {
        Files.delete(path);
        return true;
      } catch(IOException e)
      {
        System.err.println("Erro ao deletar o arquivo/diretório: " + e.getMessage());
      }
    return false;
  }

  /**
   * Método para deletar um arquivo.
   */
  public static boolean FileDelete(String filePath)
  {
    if(FileExist(filePath))
      {
        try
          {
            Path path = Paths.get(filePath);
            Files.delete(path);
            return true;
          } catch(IOException e)
          {
            System.err.println("Erro ao deletar o arquivo: " + e.getMessage());
            return false;
          }
      }
    return false;
  }
}
