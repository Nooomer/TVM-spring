package com.nooomer.tvmspring.services

import com.nooomer.tvmspring.config.StorageProperties
import com.nooomer.tvmspring.exceptions.StorageException
import com.nooomer.tvmspring.exceptions.StorageFileNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.stream.Stream
import kotlin.io.path.Path


@Service
class StorageService(properties: StorageProperties) {
    private var rootLocation: Path? = null

    fun store(file: MultipartFile):Boolean {
        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file.")
            }
            val destinationFile: Path? = rootLocation?.resolve(
                Paths.get(file.originalFilename)
            )?.normalize()?.toAbsolutePath()
            if (!destinationFile?.parent?.equals(rootLocation?.toAbsolutePath())!!) {
                // This is a security check
                throw StorageException(
                    "Cannot store file outside current directory."
                )
            }
            file.inputStream.use { inputStream ->
                Files.copy(
                    inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING
                )
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file.", e)
        }
        return true
    }

    fun loadAll(): Stream<Path>? {
        try {
           return Files.walk(this.rootLocation, 1)
                .filter { path: Path -> path != rootLocation }?.map(rootLocation!!::relativize)
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }
    }

    fun load(filename: String?): Path? {
        return rootLocation?.resolve(filename)
    }

    fun loadAsResource(filename: String): Resource {
        try {
            val file: Path? = load(filename)
            val resource: Resource = UrlResource(file?.toUri()!!)
            if (resource.exists() || resource.isReadable) {
                return resource
            } else {
                throw StorageFileNotFoundException(
                    "Could not read file: $filename"
                )
            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }
    }

    fun deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation?.toFile())
    }

    init {
        if (properties.location == "") {
            throw StorageException("File upload location can not be Empty.")
        }

        this.rootLocation = Paths.get(properties.location)
        try {
            Files.createDirectories(rootLocation)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }
    }
}