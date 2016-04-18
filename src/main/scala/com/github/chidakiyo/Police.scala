package com.github.chidakiyo

import java.io.InputStream

import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient
import com.amazonaws.services.lambda.runtime.Context

import scala.collection.JavaConverters._

class Police {
  def handler(in: InputStream, context: Context): String = {

    //    val lambdaLogger = context.getLogger()
    //    lambdaLogger.log("count = " + 12)

    val ec2 = new AmazonEC2Client(new ClasspathPropertiesFileCredentialsProvider())
    ec2.setRegion(Region.getRegion(Regions.US_WEST_2))
    println("client has been setup")


    ec2.describeInstances.getReservations.asScala.toList.map { reservation =>
      reservation.getInstances.asScala.toList.map { instance =>

        val targetTag = instance.getTags.asScala.toList.find(tag => tag.getKey.equalsIgnoreCase("Cron"))

        targetTag.map { tag =>
          val value = tag.getValue
          println(value)

        }

        val iam = new AmazonIdentityManagementClient(new ClasspathPropertiesFileCredentialsProvider())
        iam.listUsers.getUsers.asScala.toList.map{ user =>



        }
        val user = iam.getUser()
        println(user)


        val status = instance.getState
        println(status.getName)
        println(instance)

        instance.getTags.asScala.toList.map { tag =>
          println(tag.getValue)
        }

      }
    }



    return String.valueOf(13)
  }
}

object Police {
  def main(args: Array[String]): Unit = {
    new Police().handler(null, null)
  }
}
