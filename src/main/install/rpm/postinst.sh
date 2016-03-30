#
# Copyright (c) Lucas Burson
# Licensed under GNU General Public License 3.0+. See <www.gnu.org/licenses/>.
#

set -e

# After every install enable and start the service
systemctl --quiet enable ljdelight-lights.service > /dev/null
systemctl --quiet start ljdelight-lights.service > /dev/null
