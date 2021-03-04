package dpla.test.ecs

import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.{PutObjectRequest, S3Exception}

/**
  *
  */
object EcsShellMain {

  def main(args: Array[String]): Unit = {

    val bucketName = args(0)

    val filename = generateRandomInt()
    val contents = generateRandomInt()
    val objectKey = s"${args(1)}/$filename.txt"

    val region = Region.US_EAST_1
    val s3 = S3Client.builder.region(region).build
    System.out.println(s"Writing '$contents' to s3://$bucketName/$objectKey")
    val result = putS3Object(s3, bucketName, objectKey, contents.toString)
    System.out.println("Tag information: " + result)

  }

  def generateRandomInt(): Int = {
    val r = scala.util.Random
    r.nextInt(100000000)
  }

  def putS3Object(s3: S3Client, bucketName: String, objectKey: String, objectPath: String): String = {
    try {
      val putOb = PutObjectRequest
        .builder
        .bucket(bucketName)
        .key(objectKey)
        .build

      val response = s3.putObject(putOb, RequestBody.fromBytes(objectPath.getBytes()))
      return response.eTag
    } catch {
      case e: S3Exception =>
        System.err.println(e.getMessage)
        System.exit(1)
    }
    ""
  }
}
