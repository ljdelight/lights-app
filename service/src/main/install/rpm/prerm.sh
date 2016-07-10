#
# Copyright (c) Lucas Burson
# Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
#

set -e

# If totally removing the package
if [ $1 -eq 0 ]; then
    # Stop the service and don't fail if it's not running
    systemctl --quiet stop  ljdelight-lights.service > /dev/null 2>&1 || :
    systemctl --quiet disable ljdelight-lights.service > /dev/null 2>&1 || :
fi
