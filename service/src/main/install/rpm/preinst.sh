#
# Copyright (c) Lucas Burson
# Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
#

set -e

# If it's an upgrade install
if [ $1 -ne 0 ]; then
    # Stop and disable the previous instance
    systemctl --quiet stop  ljdelight-lights.service > /dev/null 2>&1 || :
    systemctl --quiet disable ljdelight-lights.service > /dev/null 2>&1 || :
fi
