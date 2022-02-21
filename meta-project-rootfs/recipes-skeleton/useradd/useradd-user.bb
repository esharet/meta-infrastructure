SUMMARY = "Example recipe for using inherit useradd"
DESCRIPTION = "This recipe serves as an example for using features from useradd.bbclass"
SECTION = "examples"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI = "file://file-user"

USER_HOME ?= "/home/user"
S = "${WORKDIR}"

PACKAGES =+ "${PN}-user"

EXCLUDE_FROM_WORLD = "1"

inherit useradd 


# You must set USERADD_PACKAGES when you inherit useradd. This
# lists which output packages will include the user/group
# creation code.
USERADD_PACKAGES = "${PN} "

# You must also set USERADD_PARAM and/or GROUPADD_PARAM when
# you inherit useradd.

# USERADD_PARAM specifies command line options to pass to the
# useradd command. Multiple users can be created by separating
# the commands with a semicolon. 
USERADD_PARAM_${PN} = "-u 1200 -d ${USER_HOME} -G sudo -r -s /bin/bash -P 'user' user; "

# user3 will be managed in the useradd-example-user3 pacakge:
# As an example, we use the -P option to set clear text password for user3
#USERADD_PARAM_${PN}-user3 = "-u 1202 -d /home/user3 -r -s /bin/bash -P 'user3' user3"

# GROUPADD_PARAM works the same way, which you set to the options
# you'd normally pass to the groupadd command. 
GROUPADD_PARAM_${PN} = "-g 880 group;"

do_install () {
	install -d -m 755 ${D}${USER_HOME}
        install -p -m 644 file-user ${D}${USER_HOME}

	# The new users and groups are created before the do_install
	# step, so you are now free to make use of them:
	chown -R user ${D}${USER_HOME}

	chgrp -R group ${D}${USER_HOME}
}

FILES_${PN} = "/home/user/* "

# Prevents do_package failures with:
# debugsources.list: No such file or directory:
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
